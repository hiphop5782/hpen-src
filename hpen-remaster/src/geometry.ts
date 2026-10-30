export function cropRect(width: number, height: number, zoom: number, x: number, y: number) {
  const w = width / Math.max(1, zoom), h = height / Math.max(1, zoom);
  return { x: Math.max(0, Math.min(width - w, x * width - w / 2)), y: Math.max(0, Math.min(height - h, y * height - h / 2)), w, h };
}
